import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';
import { REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import { defaultValue, IPhoto } from 'app/shared/model/photo.model';

export const ACTION_TYPES = {
  FETCH_PHOTO_LIST: 'homeGallery/FETCH_PHOTO_LIST',
  FETCH_PHOTO: 'homeGallery/FETCH_PHOTO',
  RESET: 'homeGallery/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPhoto>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type HomeGalleryState = Readonly<typeof initialState>;

// Reducer

export default (state: HomeGalleryState = initialState, action): HomeGalleryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PHOTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PHOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHOTO_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHOTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'res/photos';

// Actions
export const getEntities: ICrudGetAllAction<IPhoto> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PHOTO_LIST,
    payload: axios.get<IPhoto>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPhoto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PHOTO,
    payload: axios.get<IPhoto>(requestUrl)
  };
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
