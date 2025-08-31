// fixtures.ts
import { test as baseTest } from '@playwright/test';

type MyFixtures = {
  envName: 'dev' | 'staging';
  baseURL: string;
};

// Extend Playwright's base test
export const test = baseTest.extend<MyFixtures>({
  envName: async ({}, use) => {
    const env = process.env.ENV || 'dev'; // fallback to dev
    await use(env as 'dev' | 'staging');
  },

  baseURL: async ({ envName }, use) => {
    const urls = {
      dev: 'https://playwright.dev',
      staging: 'http://google.com',
    };
    await use(urls[envName]);
  },
});

export { expect } from '@playwright/test';
