import { NextPage } from 'next'

import { Row } from 'antd';
import React from 'react';

type Props = {
    children: JSX.Element,
    margin?: string;
    width?: string;
    display?: string;
}
const Container = ({margin,children, width, display}: Props)  => {
    return (
        <Row style={{ width: '100%', margin: 'auto'}}>
            <Row style={{ margin: margin || 'auto', width: width || '750px', display }}>
                {children}
            </Row>
        </Row>
    )
}

export default Container