import React from 'react';
import { Switch } from 'react-router-dom';
// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import Car from './car';
import Carent from './carent';
import Contact from './contact';
import RentHistory from './rent-history';
import Album from './album';
import Photo from './photo';
import Tag from './tag';
import Details from './details';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/details`} component={Details} />
      <ErrorBoundaryRoute path={`${match.url}/car`} component={Car} />
      <ErrorBoundaryRoute path={`${match.url}/carent`} component={Carent} />
      <ErrorBoundaryRoute path={`${match.url}/contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}/rent-history`} component={RentHistory} />
      <ErrorBoundaryRoute path={`${match.url}/album`} component={Album} />
      <ErrorBoundaryRoute path={`${match.url}/photo`} component={Photo} />
      <ErrorBoundaryRoute path={`${match.url}/tag`} component={Tag} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
