import { by, element, ElementFinder } from 'protractor';

export default class PhotoUpdatePage {
  pageTitle: ElementFinder = element(by.id('newprjApp.photo.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#photo-title'));
  descriptionInput: ElementFinder = element(by.css('textarea#photo-description'));
  imageInput: ElementFinder = element(by.css('input#file_image'));
  heightInput: ElementFinder = element(by.css('input#photo-height'));
  widthInput: ElementFinder = element(by.css('input#photo-width'));
  takenInput: ElementFinder = element(by.css('input#photo-taken'));
  uploadedInput: ElementFinder = element(by.css('input#photo-uploaded'));
  userSelect: ElementFinder = element(by.css('select#photo-user'));
  albumSelect: ElementFinder = element(by.css('select#photo-album'));
  tagSelect: ElementFinder = element(by.css('select#photo-tag'));

  getPageTitle() {
    return this.pageTitle;
  }

  setTitleInput(title) {
    this.titleInput.sendKeys(title);
  }

  getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  setDescriptionInput(description) {
    this.descriptionInput.sendKeys(description);
  }

  getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  setImageInput(image) {
    this.imageInput.sendKeys(image);
  }

  getImageInput() {
    return this.imageInput.getAttribute('value');
  }

  setHeightInput(height) {
    this.heightInput.sendKeys(height);
  }

  getHeightInput() {
    return this.heightInput.getAttribute('value');
  }

  setWidthInput(width) {
    this.widthInput.sendKeys(width);
  }

  getWidthInput() {
    return this.widthInput.getAttribute('value');
  }

  setTakenInput(taken) {
    this.takenInput.sendKeys(taken);
  }

  getTakenInput() {
    return this.takenInput.getAttribute('value');
  }

  setUploadedInput(uploaded) {
    this.uploadedInput.sendKeys(uploaded);
  }

  getUploadedInput() {
    return this.uploadedInput.getAttribute('value');
  }

  userSelectLastOption() {
    this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  userSelectOption(option) {
    this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
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
