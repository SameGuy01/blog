import React from 'react'

import style from './style.scss'

class NavigationBar extends React.Component{

    render() {
        return(
           <header>
               <div className="container">
                  <a className="logo">ABC</a>
                   <div className="nav">
                      <ul>
                          <li><a href="#"> Test 1 </a></li>
                          <li><a href="#"> Test 2  </a></li>
                          <li><a href="#"> Test 3 </a></li>
                      </ul>
                  </div>
               </div>
           </header>
        )
    }
}

export default NavigationBar