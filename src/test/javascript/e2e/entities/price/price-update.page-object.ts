import { by, element, ElementFinder } from 'protractor';

export default class PriceUpdatePage {
  pageTitle: ElementFinder = element(by.id('newprjApp.price.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  adjDateInput: ElementFinder = element(by.css('input#price-adjDate'));
  nameInput: ElementFinder = element(by.css('input#price-name'));
  serialInput: ElementFinder = element(by.css('input#price-serial'));
  priceInput: ElementFinder = element(by.css('input#price-price'));
  factorOneInput: ElementFinder = element(by.css('input#price-factorOne'));
  factorTwoInput: ElementFinder = element(by.css('input#price-factorTwo'));
  factorThreeInput: ElementFinder = element(by.css('input#price-factorThree'));
  taxInput: ElementFinder = element(by.css('input#price-tax'));
  totalInput: ElementFinder = element(by.css('input#price-total'));
  classificationSelect: ElementFinder = element(by.css('select#price-classification'));

  getPageTitle() {
    return this.pageTitle;
  }

  setAdjDateInput(adjDate) {
    this.adjDateInput.sendKeys(adjDate);
  }

  getAdjDateInput() {
    return this.adjDateInput.getAttribute('value');
  }

  setNameInput(name) {
    this.nameInput.sendKeys(name);
  }

  getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  setSerialInput(serial) {
    this.serialInput.sendKeys(serial);
  }

  getSerialInput() {
    return this.serialInput.getAttribute('value');
  }

  setPriceInput(price) {
    this.priceInput.sendKeys(price);
  }

  getPriceInput() {
    return this.priceInput.getAttribute('value');
  }

  setFactorOneInput(factorOne) {
    this.factorOneInput.sendKeys(factorOne);
  }

  getFactorOneInput() {
    return this.factorOneInput.getAttribute('value');
  }

  setFactorTwoInput(factorTwo) {
    this.factorTwoInput.sendKeys(factorTwo);
  }

  getFactorTwoInput() {
    return this.factorTwoInput.getAttribute('value');
  }

  setFactorThreeInput(factorThree) {
    this.factorThreeInput.sendKeys(factorThree);
  }

  getFactorThreeInput() {
    return this.factorThreeInput.getAttribute('value');
  }

  setTaxInput(tax) {
    this.taxInput.sendKeys(tax);
  }

  getTaxInput() {
    return this.taxInput.getAttribute('value');
  }

  setTotalInput(total) {
    this.totalInput.sendKeys(total);
  }

  getTotalInput() {
    return this.totalInput.getAttribute('value');
  }

  setClassificationSelect(classification) {
    this.classificationSelect.sendKeys(classification);
  }

  getClassificationSelect() {
    return this.classificationSelect.element(by.css('option:checked')).getText();
  }

  classificationSelectLastOption() {
    this.classificationSelect
      .all(by.tagName('option'))
      .last()
      .click();
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
