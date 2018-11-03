/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import RentHistoryComponentsPage, { RentHistoryDeleteDialog } from './rent-history.page-object';
import RentHistoryUpdatePage from './rent-history-update.page-object';

const expect = chai.expect;

describe('RentHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let rentHistoryUpdatePage: RentHistoryUpdatePage;
  let rentHistoryComponentsPage: RentHistoryComponentsPage;
  let rentHistoryDeleteDialog: RentHistoryDeleteDialog;

  before(() => {
    browser.get('/');
    navBarPage = new NavBarPage();
    navBarPage.autoSignIn();
  });

  it('should load RentHistories', async () => {
    navBarPage.getEntityPage('rent-history');
    rentHistoryComponentsPage = new RentHistoryComponentsPage();
    expect(await rentHistoryComponentsPage.getTitle().getText()).to.match(/Rent Histories/);
  });

  it('should load create RentHistory page', async () => {
    rentHistoryComponentsPage.clickOnCreateButton();
    rentHistoryUpdatePage = new RentHistoryUpdatePage();
    expect(await rentHistoryUpdatePage.getPageTitle().getText()).to.match(/Create or edit a RentHistory/);
  });

  it('should create and save RentHistories', async () => {
    const nbButtonsBeforeCreate = await rentHistoryComponentsPage.countDeleteButtons();

    rentHistoryUpdatePage.setRegDateInput('01-01-2001');
    expect(await rentHistoryUpdatePage.getRegDateInput()).to.eq('2001-01-01');
    rentHistoryUpdatePage.setStartDateInput('01-01-2001');
    expect(await rentHistoryUpdatePage.getStartDateInput()).to.eq('2001-01-01');
    rentHistoryUpdatePage.setEndDateInput('01-01-2001');
    expect(await rentHistoryUpdatePage.getEndDateInput()).to.eq('2001-01-01');
    rentHistoryUpdatePage.setTotalPaidInput('5');
    expect(await rentHistoryUpdatePage.getTotalPaidInput()).to.eq('5');
    rentHistoryUpdatePage.statusSelectLastOption();
    // rentHistoryUpdatePage.carSelectLastOption();
    rentHistoryUpdatePage.clientSelectLastOption();
    await rentHistoryUpdatePage.save();
    expect(await rentHistoryUpdatePage.getSaveButton().isPresent()).to.be.false;

    rentHistoryComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await rentHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last RentHistory', async () => {
    rentHistoryComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await rentHistoryComponentsPage.countDeleteButtons();
    await rentHistoryComponentsPage.clickOnLastDeleteButton();

    rentHistoryDeleteDialog = new RentHistoryDeleteDialog();
    expect(await rentHistoryDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/newprjApp.rentHistory.delete.question/);
    await rentHistoryDeleteDialog.clickOnConfirmButton();

    rentHistoryComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await rentHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(() => {
    navBarPage.autoSignOut();
  });
});
