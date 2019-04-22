import React from 'react';
import { Link } from 'react-router-dom';


class HeaderList extends React.Component {
  render() {
    let props = this.props;
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
          <li className="nav-item nav-link  ">
            <Link
              to={`/profiles/${props.currentUser.userId}`}
              className=" nav-link ">
              {/* <img src={props.currentUser.image} className="user-pic" alt={props.currentUser.username} /> */}
              {props.currentUser.username}
            </Link>
          </li>
        }
        {props.currentUser &&
          <button
            className="btn btn-outline-danger"
            onClick={props.logout}>
            Logout
      </button>
        }
      </ul>
    );
  }
}

export default HeaderList;
