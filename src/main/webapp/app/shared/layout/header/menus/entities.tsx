import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/car">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Car
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/contact">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Contact
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/rent-history">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Rent History
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/album">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Album
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/photo">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Photo
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/tag">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Tag
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/price">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Price
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
