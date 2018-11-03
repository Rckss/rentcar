import axios from 'axios';
import {
  ICrudDeleteAction,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  loadMoreDataWhenScrolled,
  parseHeaderForLinks
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IRentHistory } from 'app/shared/model/rent-history.model';

export const ACTION_TYPES = {
  FETCH_RENTHISTORY_LIST: 'rentHistory/FETCH_RENTHISTORY_LIST',
  FETCH_RENTHISTORY: 'rentHistory/FETCH_RENTHISTORY',
  CREATE_RENTHISTORY: 'rentHistory/CREATE_RENTHISTORY',
  UPDATE_RENTHISTORY: 'rentHistory/UPDATE_RENTHISTORY',
  DELETE_RENTHISTORY: 'rentHistory/DELETE_RENTHISTORY',
  RESET: 'rentHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRentHistory>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type RentHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: RentHistoryState = initialState, action): RentHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RENTHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RENTHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_RENTHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_RENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RENTHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RENTHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_RENTHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_RENTHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_RENTHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RENTHISTORY_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_RENTHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RENTHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_RENTHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RENTHISTORY):
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

const apiUrl = 'api/rent-historiesbyuser';

// Actions

export const getEntities: ICrudGetAllAction<IRentHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RENTHISTORY_LIST,
    payload: axios.get<IRentHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRentHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RENTHISTORY,
    payload: axios.get<IRentHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RENTHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IRentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RENTHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRentHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RENTHISTORY,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
