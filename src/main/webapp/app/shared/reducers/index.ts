import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import car, {CarState} from 'app/entities/car/car.reducer';
// prettier-ignore
import contact, {ContactState} from 'app/entities/contact/contact.reducer';
// prettier-ignore
import rentHistory, {RentHistoryState} from 'app/entities/rent-history/rent-history.reducer';
// prettier-ignore
import album, {AlbumState} from 'app/entities/album/album.reducer';
// prettier-ignore
import photo, {PhotoState} from 'app/entities/photo/photo.reducer';
// prettier-ignore
import tag, {TagState} from 'app/entities/tag/tag.reducer';
// prettier-ignore
import price, {PriceState} from 'app/entities/price/price.reducer';
// prettier-ignore
// added by me
import homeGallery, {HomeGalleryState} from 'app/modules/home/home-gallery/home-gallery.reducer';
import rentModal, { RentModalHistoryState } from 'app/usr/rent-history/helper/rent-modal.reducer';
import carent, { CarentState } from 'app/usr/carent/car.reducer';
import fcar, { fcarState } from 'app/usr/details/fcar.reducer';

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly car: CarState;
  readonly contact: ContactState;
  readonly rentHistory: RentHistoryState;
  readonly album: AlbumState;
  readonly photo: PhotoState;
  readonly tag: TagState;
  readonly price: PriceState;
  readonly loadingBar: any;
  readonly homeGallery: HomeGalleryState;
  readonly rentModal: RentModalHistoryState;
  readonly carent: CarentState;
  readonly fcar: fcarState;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  car,
  contact,
  rentHistory,
  album,
  photo,
  tag,
  price,
  loadingBar,
  homeGallery,
  rentModal,
  carent,
  fcar
});

export default rootReducer;
