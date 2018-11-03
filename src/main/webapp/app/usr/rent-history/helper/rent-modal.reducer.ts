import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IRentHistory } from 'app/shared/model/rent-history.model';

export const ACTION_TYPES = {
  FETCH_ARENTHISTORY_LIST: 'rentmodalHistory/FETCH_RENTHISTORY_LIST',
  FETCH_ARENTHISTORY: 'rentmodalHistory/FETCH_RENTHISTORY',
  CREATE_ARENTHISTORY: 'rentmodalHistory/CREATE_RENTHISTORY',
  UPDATE_ARENTHISTORY: 'rentmodalHistory/UPDATE_RENTHISTORY',
  RESET: 'rentmodalHistory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRentHistory>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  showModalLogin: true
};

export type RentModalHistoryState = Readonly<typeof initialState>;

// Reducer
export default (state: RentModalHistoryState = initialState, action): RentModalHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARENTHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        showModalLogin: true,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARENTHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_ARENTHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        showModalLogin: true,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ARENTHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARENTHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_ARENTHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_ARENTHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        showModalLogin: true,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARENTHISTORY_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        showModalLogin: true,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARENTHISTORY):
      return {
        ...state,
        loading: false,
        showModalLogin: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARENTHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_ARENTHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        showModalLogin: false,
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

const apiUrl = 'api/rent-historiesbyuser';

// Actions

export const getEntities: ICrudGetAllAction<IRentHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARENTHISTORY_LIST,
    payload: axios.get<IRentHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IRentHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARENTHISTORY,
    payload: axios.get<IRentHistory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARENTHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IRentHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARENTHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
