import React from 'react';
import Banner from './containers/Home/Banner';

const Navbar = (props) => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light mb-3" >
            <div className="collapse navbar-collapse">
                <Banner  appName="TestName"/>
                    <ul className="navbar-nav mr-auto mt-2 mt-lg-0">

                        <li className="nav-item">
                            <a className="nav-link" href="/login">Login</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/tickets">Tickets</a>
                        </li>
                        <li className="nav-item">
                        </li>
                    </ul>

            </div>
        </nav>

            );
};                   
export default Navbar   