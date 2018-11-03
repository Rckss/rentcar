import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, ICar } from 'app/shared/model/car.model';
// import { ICar, defaultValue } from 'app/shared/model/car.model.two';

export const ACTION_TYPES = {
  FETCH_CAR_LIST: 'car/FETCH_CAR_LIST',
  FETCH_CAR: 'car/FETCH_CAR',
  CREATE_CAR: 'car/CREATE_CAR',
  UPDATE_CAR: 'car/UPDATE_CAR',
  DELETE_CAR: 'car/DELETE_CAR',
  RESET: 'car/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICar>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CarState = Readonly<typeof initialState>;

// Reducer

export default (state: CarState = initialState, action): CarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAR):
    case REQUEST(ACTION_TYPES.UPDATE_CAR):
    case REQUEST(ACTION_TYPES.DELETE_CAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAR):
    case FAILURE(ACTION_TYPES.CREATE_CAR):
    case FAILURE(ACTION_TYPES.UPDATE_CAR):
    case FAILURE(ACTION_TYPES.DELETE_CAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAR):
    case SUCCESS(ACTION_TYPES.UPDATE_CAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};
// switch or add a new reducer for them!!!
const apiUrl = 'api/cars';
// const apiUrl = 'api/completecars';

// Actions

export const getEntities: ICrudGetAllAction<ICar> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CAR_LIST,
  payload: axios.get<ICar>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAR,
    payload: axios.get<ICar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
