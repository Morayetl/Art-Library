import { Col, Image, Row } from "antd";

type Props = {
    data: ResultData[];
}

export type ResultData = {
    id: string;
    type: string;
    url: string;
}
const Results = (props: Props) => {
    return (
        <Row gutter={20}>
            {
                props.data.map(image => {
                    return (
                        <Col className="antd-custom-image" span={8} key={image.id} style={{marginTop: '20px'}}>
                            <Image style={{objectFit: 'cover', height: '100%'}} src={image.url} preview={undefined} />
                        </Col>
                    )
                })
            }
        </Row>
    )
}

export default Results;