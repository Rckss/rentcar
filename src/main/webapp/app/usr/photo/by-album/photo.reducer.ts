import axios from 'axios';
import { ICrudGetOneAction } from 'react-jhipster';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IPhoto } from 'app/shared/model/photo.model';

export const ACTION_TYPES = {
  FETCH_PHOTO_LIST: 'photo/FETCH_PHOTO_LIST',
  FETCH_PHOTO: 'photo/FETCH_PHOTO',
  CREATE_PHOTO: 'photo/CREATE_PHOTO',
  UPDATE_PHOTO: 'photo/UPDATE_PHOTO',
  DELETE_PHOTO: 'photo/DELETE_PHOTO',
  SET_BLOB: 'photo/SET_BLOB',
  RESET: 'photo/RESET'
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

export type PhotoState = Readonly<typeof initialState>;

// Reducer

export default (state: PhotoState = initialState, action): PhotoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PHOTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PHOTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PHOTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };

    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/photobyalbum';

// Actions

export const getEntity: ICrudGetOneAction<IPhoto> = (albumId, photoId) => {
  const requestUrl = `${apiUrl}/${albumId}/${photoId}`;
  return {
    type: ACTION_TYPES.FETCH_PHOTO,
    payload: axios.get<IPhoto>(requestUrl)
  };
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
