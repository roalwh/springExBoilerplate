import React from 'react'
import MainNavigation from './MainNavigation'

export default function Layout(props) {
    return (
        <>
            <MainNavigation />
            {/* <main className='' style={{ backgroundColor: "rgb(33,37,41)" }}> */}
            <main className='' style={{}}>
                {props.children}
            </main>
        </>

    )
}

