import axios from 'axios';
import { ICrudGetAction } from 'react-jhipster';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';
import { defaultValue, IfCar } from 'app/shared/model/fcar.model';

export const ACTION_TYPES = {
  FETCH_FCAR: 'fcar/FETCH_FCAR',
  RESET: 'fcar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entity: defaultValue
};

export type fcarState = Readonly<typeof initialState>;

// Reducer

export default (state: fcarState = initialState, action): fcarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FCAR):
      return {
        ...state,
        errorMessage: null,
        loading: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FCAR):
      return {
        ...state,
        loading: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FCAR):
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

const apiUrl = 'api/completecars';

// Actions
export const getEntity: ICrudGetAction<any> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FCAR,
    payload: axios.get<any>(requestUrl)
  };
};

export const getEntity2 = id => dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  dispatch({
    type: ACTION_TYPES.FETCH_FCAR,
    payload: axios.get<IfCar>(requestUrl)
  });
};

export const getEntity3 = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  await dispatch({
    type: ACTION_TYPES.FETCH_FCAR,
    payload: axios.get<IfCar>(requestUrl)
  });
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
