import { by, element, ElementFinder } from 'protractor';

export default class RentHistoryUpdatePage {
  pageTitle: ElementFinder = element(by.id('newprjApp.rentHistory.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  regDateInput: ElementFinder = element(by.css('input#rent-history-regDate'));
  startDateInput: ElementFinder = element(by.css('input#rent-history-startDate'));
  endDateInput: ElementFinder = element(by.css('input#rent-history-endDate'));
  totalPaidInput: ElementFinder = element(by.css('input#rent-history-totalPaid'));
  statusSelect: ElementFinder = element(by.css('select#rent-history-status'));
  carSelect: ElementFinder = element(by.css('select#rent-history-car'));
  clientSelect: ElementFinder = element(by.css('select#rent-history-client'));

  getPageTitle() {
    return this.pageTitle;
  }

  setRegDateInput(regDate) {
    this.regDateInput.sendKeys(regDate);
  }

  getRegDateInput() {
    return this.regDateInput.getAttribute('value');
  }

  setStartDateInput(startDate) {
    this.startDateInput.sendKeys(startDate);
  }

  getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  setEndDateInput(endDate) {
    this.endDateInput.sendKeys(endDate);
  }

  getEndDateInput() {
    return this.endDateInput.getAttribute('value');
  }

  setTotalPaidInput(totalPaid) {
    this.totalPaidInput.sendKeys(totalPaid);
  }

  getTotalPaidInput() {
    return this.totalPaidInput.getAttribute('value');
  }

  setStatusSelect(status) {
    this.statusSelect.sendKeys(status);
  }

  getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  statusSelectLastOption() {
    this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  carSelectLastOption() {
    this.carSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  carSelectOption(option) {
    this.carSelect.sendKeys(option);
  }

  getCarSelect() {
    return this.carSelect;
  }

  getCarSelectedOption() {
    return this.carSelect.element(by.css('option:checked')).getText();
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
