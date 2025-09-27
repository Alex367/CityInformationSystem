import { test, expect } from '@playwright/test';

test('login test', async ({ page }) => {

  await page.goto('http://localhost:4200/login');

  await expect(page).toHaveURL(/.*login/);
});