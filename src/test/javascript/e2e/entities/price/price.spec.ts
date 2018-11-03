/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import PriceComponentsPage, { PriceDeleteDialog } from './price.page-object';
import PriceUpdatePage from './price-update.page-object';

const expect = chai.expect;

describe('Price e2e test', () => {
  let navBarPage: NavBarPage;
  let priceUpdatePage: PriceUpdatePage;
  let priceComponentsPage: PriceComponentsPage;
  let priceDeleteDialog: PriceDeleteDialog;

  before(() => {
    browser.get('/');
    navBarPage = new NavBarPage();
    navBarPage.autoSignIn();
  });

  it('should load Prices', async () => {
    navBarPage.getEntityPage('price');
    priceComponentsPage = new PriceComponentsPage();
    expect(await priceComponentsPage.getTitle().getText()).to.match(/Prices/);
  });

  it('should load create Price page', async () => {
    priceComponentsPage.clickOnCreateButton();
    priceUpdatePage = new PriceUpdatePage();
    expect(await priceUpdatePage.getPageTitle().getText()).to.match(/Create or edit a Price/);
  });

  it('should create and save Prices', async () => {
    const nbButtonsBeforeCreate = await priceComponentsPage.countDeleteButtons();

    priceUpdatePage.setAdjDateInput('01-01-2001');
    expect(await priceUpdatePage.getAdjDateInput()).to.eq('2001-01-01');
    priceUpdatePage.setNameInput('name');
    expect(await priceUpdatePage.getNameInput()).to.match(/name/);
    priceUpdatePage.setSerialInput('serial');
    expect(await priceUpdatePage.getSerialInput()).to.match(/serial/);
    priceUpdatePage.setPriceInput('5');
    expect(await priceUpdatePage.getPriceInput()).to.eq('5');
    priceUpdatePage.setFactorOneInput('5');
    expect(await priceUpdatePage.getFactorOneInput()).to.eq('5');
    priceUpdatePage.setFactorTwoInput('5');
    expect(await priceUpdatePage.getFactorTwoInput()).to.eq('5');
    priceUpdatePage.setFactorThreeInput('5');
    expect(await priceUpdatePage.getFactorThreeInput()).to.eq('5');
    priceUpdatePage.setTaxInput('5');
    expect(await priceUpdatePage.getTaxInput()).to.eq('5');
    priceUpdatePage.setTotalInput('5');
    expect(await priceUpdatePage.getTotalInput()).to.eq('5');
    priceUpdatePage.classificationSelectLastOption();
    await priceUpdatePage.save();
    expect(await priceUpdatePage.getSaveButton().isPresent()).to.be.false;

    priceComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await priceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Price', async () => {
    priceComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await priceComponentsPage.countDeleteButtons();
    await priceComponentsPage.clickOnLastDeleteButton();

    priceDeleteDialog = new PriceDeleteDialog();
    expect(await priceDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/newprjApp.price.delete.question/);
    await priceDeleteDialog.clickOnConfirmButton();

    priceComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await priceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(() => {
    navBarPage.autoSignOut();
  });
});
