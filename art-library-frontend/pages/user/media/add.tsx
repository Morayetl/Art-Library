import { InboxOutlined } from "@ant-design/icons";
import { Button, Col, Divider, Form, Image, Input, Layout, message, Row, Select } from "antd";
import Dragger from "antd/lib/upload/Dragger";
import { UploadFile } from "antd/lib/upload/interface";
import Head from "next/head";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import uploadApi from "../../../components/api/upload";
import mediaApi from "../../../components/api/media";
import { UserAccessRedirect } from "../../../components/helpers/auth-helper";
import Container from "../../../components/shared/Container";
import CustomFooter from "../../../components/shared/CustomFooter";
import Menu from "../../../components/shared/Menu";
import { IMedia } from "../../../components/types/IMedia";

const { Header, Content } = Layout;

type Props = {
    formType?: 'edit' | 'add'
}
const AddMediaPage = (props: Props) => {
    const router = useRouter()
    const [preview, setPreview] = useState<string>()
    const [fileType, setFileType] = useState<string>()
    const [loading, setLoading] = useState<boolean>(false)
    const { id } = router.query
    const [form] = Form.useForm();
    const file: { file: UploadFile<any>, fileList: Array<UploadFile<any>> } = Form.useWatch('file', form);

    const onFinish = (value: IMedia) => {
        if (props.formType === 'edit') {
            setLoading(true)
            uploadApi
                .updateMedia(value, id as string)
                .then(() => {
                    message.success('Media successfully updated', 10);
                }).finally(() => setLoading(false));
        } else {
            const file: UploadFile = value.file.file.originFileObj!
            const allowedTypes = ['jpg', 'jpeg', 'png']

            const validType = allowedTypes.some(t => file?.type?.includes(t));
            if(!validType){
                message.error('Media not supported. Valid types are ' + allowedTypes.join(', '), 10);
                return;
            }

            setLoading(true)
            uploadApi
                .addMedia(value)
                .then((res) => {
                    message.success('Media successfully added', 10);
                    form.resetFields()
                }).finally(() => setLoading(false));
        }

    }
    useEffect(() => {
        if (id) {
            mediaApi.getMeta(id as string).then((res) => {
                const media = res.data
                const fieldValues = {
                    title: media.title,
                    description: media.description,
                    tags: media.tags
                }
                setFileType(media.type);
                form.setFieldsValue(fieldValues)
                setPreview(media.src);
            })
        }
    }, [id])

    useEffect(() => {
        if (props.formType === 'edit') {
            return;
        }

        if (file?.fileList?.length > 0) {
            const objectUrl = URL.createObjectURL(file.file.originFileObj as Blob)
            setPreview(objectUrl)
        } else {
            setPreview("")
        }
    }, [file?.file])

    return (
        <Layout>
            <Head>
                <title>{`Add media`}</title>
            </Head>
            <Header style={{ padding: 0, display: 'block', background: 'rgba(0, 0, 0, 0.85)' }}>
                <Row style={{ width: '100%', zIndex: 1 }}>
                    <Menu />
                </Row>
            </Header>
            <Content style={{ marginTop: '30px', marginBottom: '20px', marginLeft: '1px' }}>
                <Container margin="auto" display='block'>
                    <>
                        <Row style={{ width: '100%' }} justify="space-between">
                            <Col>
                                <h1>
                                    {props.formType === 'edit' ? 'Edit media' : 'Add media'}
                                </h1>
                            </Col>
                        </Row>
                        <Divider style={{ marginTop: '10px', marginBottom: '15px' }} dashed />
                        <Row style={{ width: '100%', marginBottom: '20px' }}>
                            <Form
                                name="basic"
                                labelCol={{ span: 8 }}
                                initialValues={{ remember: true }}
                                onFinish={onFinish}
                                onFinishFailed={() => { }}
                                autoComplete="off"
                                size="large"
                                layout="vertical"
                                style={{ width: '100%' }}
                                form={form}
                            >
                                <Form.Item
                                    label="Title"
                                    name="title"
                                    rules={[{ required: true /*message: 'Please input your username!' */ }]}
                                >
                                    <Input />
                                </Form.Item>

                                <Form.Item
                                    label="Description"
                                    name="description"
                                    rules={[{ required: true /*message: 'Please input your username!' */ }]}
                                >
                                    <Input.TextArea rows={5} />
                                </Form.Item>


                                <Form.Item
                                    label="Tags"
                                    name="tags"
                                    rules={[{ required: true /*message: 'Please input your username!' */ }]}
                                >
                                    <Select mode="tags" style={{ width: '100%' }} placeholder="Add tags" />
                                </Form.Item>
                                {
                                    ((file?.file?.originFileObj?.type.startsWith('image') || fileType?.startsWith('image')) && preview) && (
                                        <Row style={{ display: 'flex', width: '100%', justifyContent: 'center' }}>
                                            <Image width="auto" height="400px" style={{ objectFit: 'contain'}} src={preview} />
                                        </Row>
                                    )
                                }

                                {
                                    props?.formType !== 'edit' && (
                                        <Form.Item
                                            label="File"
                                            name="file"
                                            rules={[{ required: true /*message: 'Please input your username!' */ }]}
                                        >
                                            <Dragger maxCount={1}>
                                                <p className="ant-upload-drag-icon">
                                                    <InboxOutlined />
                                                </p>
                                                <p className="ant-upload-text">Click or drag file to this area to upload</p>
                                                <p className="ant-upload-hint">
                                                    Support for a single or bulk upload. Strictly prohibit from uploading company data or other
                                                    band files
                                                </p>
                                            </Dragger>
                                        </Form.Item>
                                    )
                                }
                                <Row style={{ display: 'flex', width: '100%', justifyContent: 'end' }}>
                                    <Button type="primary" htmlType="submit" style={{ marginLeft: 'auto' }} loading={loading}>
                                        Submit
                                    </Button>
                                </Row>
                            </Form>
                        </Row>
                    </>
                </Container>
            </Content>
            <CustomFooter />
        </Layout>
    )
}


export const getServerSideProps = UserAccessRedirect

export default AddMediaPage