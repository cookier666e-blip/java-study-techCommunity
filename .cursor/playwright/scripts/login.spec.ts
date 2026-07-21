/**
 * YQN QA 环境登录获取 Cookie 脚本（基于 Playwright 测试框架，完全自包含）
 *
 * 使用方式:
 *   npx playwright test -c /path/to/.cursor/playwright/scripts login.spec.ts --reporter=list
 *
 * 环境变量:
 *   EMAIL    登录邮箱（默认: liurihua@yqn.com）
 *   CODE     验证码（默认: 1111，QA 环境固定值）
 *   ENV      环境前缀（默认: qa4，对应 qa4-work.yqn.com）
 *   OUTPUT   输出文件路径（默认: <调用项目目录>/tests/e2e/.auth/user.json）
 *   CHROME   Chrome 可执行文件路径（自动检测）
 */

import { test, expect } from '@playwright/test';
import * as fs from 'fs';
import * as path from 'path';

const EMAIL = process.env.EMAIL || 'liurihua@yqn.com';
const CODE = process.env.CODE || '1111';
const ENV_PREFIX = process.env.ENV || 'qa4';
const OUTPUT = process.env.OUTPUT || path.join(process.cwd(), 'tests/e2e/.auth/user.json');

function detectChromePath(): string | undefined {
  const candidates = [
    '/Applications/Google Chrome.app/Contents/MacOS/Google Chrome',
    '/usr/bin/google-chrome',
    '/usr/bin/google-chrome-stable',
    '/usr/bin/chromium-browser',
    '/usr/bin/chromium',
    'C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe',
    'C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe',
  ];
  return candidates.find((p) => fs.existsSync(p));
}

const chromePath = process.env.CHROME || detectChromePath();

test.use({
  launchOptions: {
    executablePath: chromePath,
    args: ['--ignore-certificate-errors', '--no-sandbox'],
  },
});

test('获取 QA 环境 cookies', async ({ browser }) => {
  const loginUrl = `https://${ENV_PREFIX}-work.yqn.com/login`;

  console.log('=== YQN QA 环境登录获取 Cookie ===');
  console.log(`  登录地址: ${loginUrl}`);
  console.log(`  登录账号: ${EMAIL}`);
  console.log(`  浏览器:   ${chromePath || 'Playwright 内置 Chromium'}`);
  console.log(`  输出路径: ${OUTPUT}`);

  const context = await browser.newContext({ ignoreHTTPSErrors: true });
  const page = await context.newPage();

  // 1. 访问登录页
  console.log('[step 1] 正在打开登录页...');
  await page.goto(loginUrl, { waitUntil: 'domcontentloaded' });

  // 2. 检测登录模式，如果是密码登录则切换到验证码登录
  const codeInputCheck = page.locator(
    'input[placeholder*="Verification"], input[placeholder*="验证码"], input[placeholder*="code"], input[placeholder*="Code"]'
  ).first();
  const isCodeMode = await codeInputCheck.isVisible({ timeout: 3000 }).catch(() => false);
  if (!isCodeMode) {
    console.log('[step 2] 当前为密码登录模式，正在切换到验证码登录...');
    const switchLink = page.locator(
      'a:has-text("验证码登录"), a:has-text("Verification code"), a:has-text("Code login"), ' +
      'span:has-text("验证码登录"), span:has-text("Verification code"), span:has-text("Code login"), ' +
      'div:has-text("验证码登录"):not(:has(div)), div:has-text("Verification code"):not(:has(div))'
    ).first();
    await switchLink.waitFor({ state: 'visible', timeout: 5000 });
    await switchLink.click();
    await codeInputCheck.waitFor({ state: 'visible', timeout: 5000 });
    console.log('[step 2] 已切换到验证码登录模式');
  } else {
    console.log('[step 2] 当前已是验证码登录模式');
  }

  // 3. 输入邮箱
  console.log(`[step 3] 正在输入邮箱: ${EMAIL}`);
  const emailInput = page.locator(
    'input[placeholder*="phone"], input[placeholder*="email"], input[placeholder*="邮箱"], input[placeholder*="账号"]'
  ).first();
  await emailInput.waitFor({ state: 'visible', timeout: 15000 });
  await emailInput.fill(EMAIL);

  // 4. 输入验证码（QA 环境固定验证码，无需点击发送验证码）
  console.log(`[step 4] 正在输入验证码: ${CODE}`);
  const codeInput = page.locator(
    'input[placeholder*="Verification"], input[placeholder*="验证码"], input[placeholder*="code"], input[placeholder*="Code"]'
  ).first();
  await codeInput.waitFor({ state: 'visible', timeout: 5000 });
  await codeInput.fill(CODE);

  // 5. 点击登录按钮
  console.log('[step 5] 正在点击登录按钮...');
  const submitBtn = page.locator(
    'button:has-text("Yangshan"), button:has-text("登录"), button:has-text("登 录"), button:has-text("Login"), button[type="submit"]'
  ).first();
  await submitBtn.waitFor({ state: 'visible', timeout: 5000 });
  await submitBtn.click();

  // 6. 等待登录成功
  console.log('[step 6] 等待登录成功...');
  await page.waitForURL(
    (url) => !url.pathname.includes('/login'),
    { timeout: 30000 }
  );

  // 7. 获取 cookies
  const cookies = await context.cookies();
  expect(cookies.length).toBeGreaterThan(0);

  console.log(`\n[success] 登录成功！获取到 ${cookies.length} 个 cookies:`);
  cookies.forEach((c) => {
    const expires = c.expires > 0 ? new Date(c.expires * 1000).toLocaleDateString() : 'session';
    console.log(`  - ${c.name} (domain: ${c.domain}, expires: ${expires})`);
  });

  // 8. 保存 Playwright storageState 格式
  const outputDir = path.dirname(OUTPUT);
  if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir, { recursive: true });
  }
  await context.storageState({ path: OUTPUT });
  console.log(`\n[saved] StorageState 已保存到: ${OUTPUT}`);

  const simplePath = OUTPUT.replace('.json', '.cookies.json');
  const simpleCookies = cookies.map((c) => ({
    name: c.name,
    value: c.value,
    domain: c.domain,
  }));
  fs.writeFileSync(simplePath, JSON.stringify(simpleCookies, null, 2));
  console.log(`[saved] 简化 Cookies 已保存到: ${simplePath}`);

  const yqnCookies = cookies.filter((c) => c.domain === '.yqn.com' || c.domain.endsWith('.yqn.com'));
  if (yqnCookies.length > 0) {
    const cookieStr = yqnCookies.map((c) => `${c.name}=${c.value}`).join('; ');
    console.log('\n[curl] Cookie Header:');
    console.log(`  Cookie: ${cookieStr}`);
  }

  await context.close();
  console.log('\n=== 完成 ===');
});
