import { Layout, Row, Typography } from "antd"
import { SITE_NAME } from "../constants";

const { Footer } = Layout;
const { Title, Paragraph ,Text} = Typography;
const date = new Date();

const CustomFooter = () => {
    return(
        <Footer style={{padding: 0,background: 'transparent'}} className="custom-footer">
            <div style={{width: '100%', background: "rgba(40,40,40,1)", display: 'flex', paddingTop: '30px', justifyContent: 'center'}}>
                <h2>
                    {SITE_NAME}
                </h2>
            </div>
            <div style={{width: '100%', background: "rgba(40,40,40,1)", display: 'flex', justifyContent: 'center'}}>
                <Paragraph style={{width: '500px', textAlign: 'center'}}>
                    Discover new Royalty Images, Picture and Videos from our website. You can use these arts however you want. 
                </Paragraph>
            </div>
            <div style={{width: '100%', background: "rgba(10,10,10,1)", display: 'flex', justifyContent: 'center', paddingTop: '20px', paddingBottom: '20px'}}>
                <Text style={{width: '500px', textAlign: 'center'}}>
                    {`Copyright ©${date.getUTCFullYear()} ${SITE_NAME}`}
                </Text>
            </div>
        </Footer>
    )
}

export default CustomFooter
