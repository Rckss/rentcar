import { by, element, ElementFinder } from 'protractor';

export default class ContactUpdatePage {
  pageTitle: ElementFinder = element(by.id('newprjApp.contact.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  phoneNumberInput: ElementFinder = element(by.css('input#contact-phoneNumber'));
  birthdayInput: ElementFinder = element(by.css('input#contact-birthday'));
  addressOneInput: ElementFinder = element(by.css('input#contact-addressOne'));
  addressTwoInput: ElementFinder = element(by.css('input#contact-addressTwo'));
  postalCodeInput: ElementFinder = element(by.css('input#contact-postalCode'));
  cityInput: ElementFinder = element(by.css('input#contact-city'));
  stateProvinceInput: ElementFinder = element(by.css('input#contact-stateProvince'));
  countryNameInput: ElementFinder = element(by.css('input#contact-countryName'));
  clientSelect: ElementFinder = element(by.css('select#contact-client'));
  photoSelect: ElementFinder = element(by.css('select#contact-photo'));

  getPageTitle() {
    return this.pageTitle;
  }

  setPhoneNumberInput(phoneNumber) {
    this.phoneNumberInput.sendKeys(phoneNumber);
  }

  getPhoneNumberInput() {
    return this.phoneNumberInput.getAttribute('value');
  }

  setBirthdayInput(birthday) {
    this.birthdayInput.sendKeys(birthday);
  }

  getBirthdayInput() {
    return this.birthdayInput.getAttribute('value');
  }

  setAddressOneInput(addressOne) {
    this.addressOneInput.sendKeys(addressOne);
  }

  getAddressOneInput() {
    return this.addressOneInput.getAttribute('value');
  }

  setAddressTwoInput(addressTwo) {
    this.addressTwoInput.sendKeys(addressTwo);
  }

  getAddressTwoInput() {
    return this.addressTwoInput.getAttribute('value');
  }

  setPostalCodeInput(postalCode) {
    this.postalCodeInput.sendKeys(postalCode);
  }

  getPostalCodeInput() {
    return this.postalCodeInput.getAttribute('value');
  }

  setCityInput(city) {
    this.cityInput.sendKeys(city);
  }

  getCityInput() {
    return this.cityInput.getAttribute('value');
  }

  setStateProvinceInput(stateProvince) {
    this.stateProvinceInput.sendKeys(stateProvince);
  }

  getStateProvinceInput() {
    return this.stateProvinceInput.getAttribute('value');
  }

  setCountryNameInput(countryName) {
    this.countryNameInput.sendKeys(countryName);
  }

  getCountryNameInput() {
    return this.countryNameInput.getAttribute('value');
  }

  clientSelectLastOption() {
    this.clientSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  clientSelectOption(option) {
    this.clientSelect.sendKeys(option);
  }

  getClientSelect() {
    return this.clientSelect;
  }

  getClientSelectedOption() {
    return this.clientSelect.element(by.css('option:checked')).getText();
  }

  photoSelectLastOption() {
    this.photoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  photoSelectOption(option) {
    this.photoSelect.sendKeys(option);
  }

  getPhotoSelect() {
    return this.photoSelect;
  }

  getPhotoSelectedOption() {
    return this.photoSelect.element(by.css('option:checked')).getText();
  }

  save() {
    return this.saveButton.click();
  }

  cancel() {
    this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
