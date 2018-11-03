/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import CarComponentsPage, { CarDeleteDialog } from './car.page-object';
import CarUpdatePage from './car-update.page-object';

const expect = chai.expect;

describe('Car e2e test', () => {
  let navBarPage: NavBarPage;
  let carUpdatePage: CarUpdatePage;
  let carComponentsPage: CarComponentsPage;
  let carDeleteDialog: CarDeleteDialog;

  before(() => {
    browser.get('/');
    navBarPage = new NavBarPage();
    navBarPage.autoSignIn();
  });

  it('should load Cars', async () => {
    navBarPage.getEntityPage('car');
    carComponentsPage = new CarComponentsPage();
    expect(await carComponentsPage.getTitle().getText()).to.match(/Cars/);
  });

  it('should load create Car page', async () => {
    carComponentsPage.clickOnCreateButton();
    carUpdatePage = new CarUpdatePage();
    expect(await carUpdatePage.getPageTitle().getText()).to.match(/Create or edit a Car/);
  });

  it('should create and save Cars', async () => {
    const nbButtonsBeforeCreate = await carComponentsPage.countDeleteButtons();

    carUpdatePage.setNameInput('name');
    expect(await carUpdatePage.getNameInput()).to.match(/name/);
    carUpdatePage.setSerialInput('serial');
    expect(await carUpdatePage.getSerialInput()).to.match(/serial/);
    carUpdatePage.setManufacturerInput('manufacturer');
    expect(await carUpdatePage.getManufacturerInput()).to.match(/manufacturer/);
    carUpdatePage.setModelInput('01-01-2001');
    expect(await carUpdatePage.getModelInput()).to.eq('2001-01-01');
    carUpdatePage.setColorInput('color');
    expect(await carUpdatePage.getColorInput()).to.match(/color/);
    carUpdatePage.classificationSelectLastOption();
    carUpdatePage.albumSelectLastOption();
    // carUpdatePage.tagSelectLastOption();
    carUpdatePage.photoSelectLastOption();
    await carUpdatePage.save();
    expect(await carUpdatePage.getSaveButton().isPresent()).to.be.false;

    carComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await carComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Car', async () => {
    carComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await carComponentsPage.countDeleteButtons();
    await carComponentsPage.clickOnLastDeleteButton();

    carDeleteDialog = new CarDeleteDialog();
    expect(await carDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/newprjApp.car.delete.question/);
    await carDeleteDialog.clickOnConfirmButton();

    carComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await carComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(() => {
    navBarPage.autoSignOut();
  });
});
