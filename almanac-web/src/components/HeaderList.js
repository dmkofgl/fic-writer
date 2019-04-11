import React from 'react';
import { Link } from 'react-router-dom';

 const HeaderList = props => {
  return (
    <ul className="nav  pull-xs-right">

      <li className="nav-item nav-link">
        <Link to="/" className="nav-link">
          Home
        </Link>
      </li>

      {!props.currentUser &&
        <li className="nav-item nav-link">
          <Link to="/login" className="nav-link">
            Sign in
          </Link>
        </li>}

      {!props.currentUser &&
        <li className="nav-item nav-link">
          <Link to="/register" className="nav-link">
            Sign up
          </Link>
        </li>
      }

      {props.currentUser &&
        <li className="nav-item nav-link">
          <Link to="/editor" className="nav-link">
            <i className="ion-compose"></i>&nbsp;New Post
        </Link>
        </li>
      }
      {props.currentUser &&
        <li className="nav-item nav-link">
          <Link to="/settings" className="nav-link">
            <i className="ion-gear-a"></i>&nbsp;Settings
        </Link>
        </li>
      }

      {props.currentUser &&
        <li className="nav-item nav-link">
          <Link
            to={`/@${props.currentUser.username}`}
            className="nav-link nav-link">
            <img src={props.currentUser.image} className="user-pic" alt={props.currentUser.username} />
            {props.currentUser.username}
          </Link>
        </li>
      }
    </ul>
  );
}
export default HeaderList;
