import { DeleteOutlined, EditOutlined } from "@ant-design/icons";
import { Button, Image, Input, Layout, List, Row, Skeleton, Space } from "antd";
import Item from "antd/lib/list/Item";
import { NextPage } from "next";
import Head from "next/head";
import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import searchApi from "../../../components/api/search";
import uploadApi from "../../../components/api/upload";
import Container from "../../../components/shared/Container";
import CustomFooter from "../../../components/shared/CustomFooter";
import Menu from "../../../components/shared/Menu";
import { PhotoPropsCustom } from "../../../components/types/PhotoPropsCustom";
const { Header, Content } = Layout;


const IconText = ({ icon, text }: { icon: React.FC; text: string }) => (
    <Space>
        {React.createElement(icon)}
        {text}
    </Space>
);

const { Search } = Input;

const MediaPage: NextPage = () => {
    const router = useRouter();
    const [photos, setPhotos] = useState<Array<PhotoPropsCustom>>([]);
    const [page, setPage] = useState<number>(0);
    const [amountOfResults, setamountOfResults] = useState<number>(0);
    const [resultNotFound, setResultNotFound] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [query, setQuery] = useState<string>("");
    const [pageSize, setPageSize] = useState<number>(0);


    useEffect(() => {
        const q = ""
        // search for all results with params

        loadImages(0, q, true);
        setQuery(q);
    }, [])


    const loadImages = (newPage?: number, searchQuery?: string, newSearch?: boolean) => {
        let nextPage = newPage;

        if (loading) {
            return;
        }
        if (newSearch) {
            nextPage = 0;
        }

        if (newSearch) {
            setQuery(searchQuery || '');
        }

        const q = newSearch ? searchQuery || '' : query
        setLoading(true);
        searchApi.searchUser(q, nextPage || 0)
            .then((res) => {
                const result = res.data
                setPage(result.page);
                setPhotos(result.results);
                setamountOfResults(result.count);
                setLoading(false);
                setPageSize(result.pageSize)
                setResultNotFound(result.results.length === 0);
            })
            .catch(() => {
                //setPhotos(testDataImages)
                setLoading(false);
            });
    }

    const deleteImage = (id: string, confirm = false) => {
        if (confirm) {
            uploadApi.deleteMedia(
                id
            ).then(() => {
                loadImages(page)
            })
        }
    }

    return (
        <Layout>
            <Head>
                <title>{`Pictures`}</title>
            </Head>
            <Header style={{ padding: 0, display: 'block', background: 'rgba(0, 0, 0, 0.85)' }}>
                <Row style={{ width: '100%', zIndex: 1 }}>
                    <Menu />
                </Row>
            </Header>
            <Content style={{ marginTop: '30px', marginBottom: '20px', marginLeft: '1px' }}>
                <Container margin="auto" display='block'>
                    <>
                        <Row style={{width: '100%'}}>
                            <Search size="large"  style={{width: '100%', marginBottom: '20px'}} placeholder="Search for something" onSearch={(value) => loadImages(0, value, true)}/>
                        </Row>
                        <List
                            itemLayout="horizontal"
                            size="large"
                            dataSource={photos}
                            pagination={
                                {
                                    pageSize: pageSize,
                                    current: (page +1),
                                    total: amountOfResults,
                                    onChange: ((page) => loadImages(page - 1))
                                }
                            }
                            renderItem={(item: PhotoPropsCustom) => (
                                <List.Item
                                    key={item.id}
                                    actions={[
                                        <Button key={item.id} type="primary" icon={<EditOutlined />} onClick={()=> router.push('/user/media/edit/'+ item.id)} size="large" />,
                                        <Button key={item.id} danger icon={<DeleteOutlined />} size="large" onClick={() => deleteImage(item.id || '', true)} />
                                    ]}

                                >
                                    <Skeleton avatar title={false} loading={loading} active>
                                        <List.Item.Meta

                                            title={<a href="#">{item.title}</a>}
                                            description={item.description}
                                        />
                                        <div>
                                            <Image
                                                width="50px"
                                                height="auto"
                                                src={item.src}
                                            />
                                        </div>
                                    </Skeleton>

                                </List.Item>
                            )}
                        >
                        </List>
                    </>

                </Container>

            </Content>
            <CustomFooter />
        </Layout>
    )
}

export default MediaPage