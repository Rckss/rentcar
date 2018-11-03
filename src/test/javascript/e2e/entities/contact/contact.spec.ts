/* tslint:disable no-unused-expression */
import { browser } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import ContactComponentsPage, { ContactDeleteDialog } from './contact.page-object';
import ContactUpdatePage from './contact-update.page-object';

const expect = chai.expect;

describe('Contact e2e test', () => {
  let navBarPage: NavBarPage;
  let contactUpdatePage: ContactUpdatePage;
  let contactComponentsPage: ContactComponentsPage;
  let contactDeleteDialog: ContactDeleteDialog;

  before(() => {
    browser.get('/');
    navBarPage = new NavBarPage();
    navBarPage.autoSignIn();
  });

  it('should load Contacts', async () => {
    navBarPage.getEntityPage('contact');
    contactComponentsPage = new ContactComponentsPage();
    expect(await contactComponentsPage.getTitle().getText()).to.match(/Contacts/);
  });

  it('should load create Contact page', async () => {
    contactComponentsPage.clickOnCreateButton();
    contactUpdatePage = new ContactUpdatePage();
    expect(await contactUpdatePage.getPageTitle().getText()).to.match(/Create or edit a Contact/);
  });

  it('should create and save Contacts', async () => {
    const nbButtonsBeforeCreate = await contactComponentsPage.countDeleteButtons();

    contactUpdatePage.setPhoneNumberInput('phoneNumber');
    expect(await contactUpdatePage.getPhoneNumberInput()).to.match(/phoneNumber/);
    contactUpdatePage.setBirthdayInput('01-01-2001');
    expect(await contactUpdatePage.getBirthdayInput()).to.eq('2001-01-01');
    contactUpdatePage.setAddressOneInput('addressOne');
    expect(await contactUpdatePage.getAddressOneInput()).to.match(/addressOne/);
    contactUpdatePage.setAddressTwoInput('addressTwo');
    expect(await contactUpdatePage.getAddressTwoInput()).to.match(/addressTwo/);
    contactUpdatePage.setPostalCodeInput('postalCode');
    expect(await contactUpdatePage.getPostalCodeInput()).to.match(/postalCode/);
    contactUpdatePage.setCityInput('city');
    expect(await contactUpdatePage.getCityInput()).to.match(/city/);
    contactUpdatePage.setStateProvinceInput('stateProvince');
    expect(await contactUpdatePage.getStateProvinceInput()).to.match(/stateProvince/);
    contactUpdatePage.setCountryNameInput('countryName');
    expect(await contactUpdatePage.getCountryNameInput()).to.match(/countryName/);
    contactUpdatePage.clientSelectLastOption();
    contactUpdatePage.photoSelectLastOption();
    await contactUpdatePage.save();
    expect(await contactUpdatePage.getSaveButton().isPresent()).to.be.false;

    contactComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Contact', async () => {
    contactComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await contactComponentsPage.countDeleteButtons();
    await contactComponentsPage.clickOnLastDeleteButton();

    contactDeleteDialog = new ContactDeleteDialog();
    expect(await contactDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/newprjApp.contact.delete.question/);
    await contactDeleteDialog.clickOnConfirmButton();

    contactComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await contactComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(() => {
    navBarPage.autoSignOut();
  });
});
