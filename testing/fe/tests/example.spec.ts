// import { test } from '@playwright/test';
import { test, expect } from '../fixtures/demo';

// test('has title', async ({ page }) => {
//   // await page.goto('https://playwright.dev/');
//   // await page.goto('/');

//   // Expect a title "to contain" a substring.
//   // await expect(page).toHaveTitle(/Playwright/);
// });

test('navigate to baseURL based on environment', async ({ page, baseURL, envName }) => {
  console.log(`Running on environment: ${envName}`);
  await page.goto(`${baseURL}/login`);

  await expect(page).toHaveURL(/.*login/);
});