import { by, element, ElementFinder } from 'protractor';

export default class CarUpdatePage {
  pageTitle: ElementFinder = element(by.id('newprjApp.car.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#car-name'));
  serialInput: ElementFinder = element(by.css('input#car-serial'));
  manufacturerInput: ElementFinder = element(by.css('input#car-manufacturer'));
  modelInput: ElementFinder = element(by.css('input#car-model'));
  colorInput: ElementFinder = element(by.css('input#car-color'));
  classificationSelect: ElementFinder = element(by.css('select#car-classification'));
  albumSelect: ElementFinder = element(by.css('select#car-album'));
  tagSelect: ElementFinder = element(by.css('select#car-tag'));
  photoSelect: ElementFinder = element(by.css('select#car-photo'));

  getPageTitle() {
    return this.pageTitle;
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

  setManufacturerInput(manufacturer) {
    this.manufacturerInput.sendKeys(manufacturer);
  }

  getManufacturerInput() {
    return this.manufacturerInput.getAttribute('value');
  }

  setModelInput(model) {
    this.modelInput.sendKeys(model);
  }

  getModelInput() {
    return this.modelInput.getAttribute('value');
  }

  setColorInput(color) {
    this.colorInput.sendKeys(color);
  }

  getColorInput() {
    return this.colorInput.getAttribute('value');
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
  albumSelectLastOption() {
    this.albumSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  albumSelectOption(option) {
    this.albumSelect.sendKeys(option);
  }

  getAlbumSelect() {
    return this.albumSelect;
  }

  getAlbumSelectedOption() {
    return this.albumSelect.element(by.css('option:checked')).getText();
  }

  tagSelectLastOption() {
    this.tagSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  tagSelectOption(option) {
    this.tagSelect.sendKeys(option);
  }

  getTagSelect() {
    return this.tagSelect;
  }

  getTagSelectedOption() {
    return this.tagSelect.element(by.css('option:checked')).getText();
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
