import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Car from './car';
import CarDetail from './car-detail';

import Rent from 'app/usr/rent-history/helper/rent';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarDetail} />
      <ErrorBoundaryRoute path={`${match.url}/:id/rent`} component={Rent} />
      <ErrorBoundaryRoute path={match.url} component={Car} />
    </Switch>
  </>
);

export default Routes;
