
import { BackTop, Layout, Result, Row } from 'antd'
import { AxiosError } from 'axios'
import { NextPage } from 'next'
import Head from 'next/head'
import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'
import mediaApi, { MEDIA_META_URL } from '../../components/api/media'
import { BACKEND_URL, FRONTEND_URL, SITE_NAME } from '../../components/constants'
import Container from '../../components/shared/Container'
import CustomFooter from '../../components/shared/CustomFooter'
import GalleryModalContent from '../../components/shared/GalleryModalContent'
import Menu from '../../components/shared/Menu'
import { PhotoPropsCustom } from '../../components/types/PhotoPropsCustom'

const { Header, Content } = Layout;
type PreviewPageProps = {
    photos?: Array<PhotoPropsCustom>
}
const PreviewPage: NextPage = (props: PreviewPageProps) => {
    const router = useRouter()
    const { id } = router.query
    const [loading, setLoading] = useState<boolean>(true);
    const [pageNotFound, setPageNotFound] = useState<boolean>(false);
    const [pageError, setPageError] = useState<boolean>(false);
    const [photos, setPhotos] = useState<Array<PhotoPropsCustom>>(props.photos || []);

    useEffect(() => {

        if (!id) {
            return;
        }

        mediaApi.getMeta(id as string)
            .then((res) => {
                setPhotos([res.data])
                setLoading(false);
            }).catch((err: AxiosError) => {
                if (err.response?.status === 404) {
                    setPageNotFound(true)
                }

                setPageError(true);
                setLoading(false);
            })
    }, [id])


    const photosExist = photos.length > 0
    const pageLoaded = photosExist && !loading
    return (
        <Layout>
            <Head>
                {(pageNotFound || pageError) ? (
                    <>
                        <title>{`${SITE_NAME} - ${(pageError && !pageNotFound) ? 'Oops something went wrong' : 'Page not found'}`}</title>
                    </>
                ) : photosExist ? (
                    <>
                        <title>{`${SITE_NAME} - ${photos[0].title}`}</title>
                        <meta name="title" content={`${SITE_NAME} - ${photos[0].title}`} />
                        <meta name="description" content={`${photos[0].description}`} />

                        <meta property="og:type" content="website" />
                        <meta property="og:url" content={FRONTEND_URL + '/preview/' + photos[0].slug} />
                        <meta property="og:title" content={`${SITE_NAME} - ${photos[0].title}`} />
                        <meta property="og:description" content={`${photos[0].description}`} />
                        <meta property="og:image" content={photos[0].src} />

                        <meta property="twitter:card" content="summary_large_image" />
                        <meta property="twitter:url" content={FRONTEND_URL + '/preview/' + photos[0].slug} />
                        <meta property="twitter:title" content={`${SITE_NAME} - ${photos[0].title}`} />
                        <meta property="twitter:description" content={`${photos[0].description}`} />
                        <meta property="twitter:image" content={photos[0].src} />
                    </>
                ) : null
                }
            </Head>
            <BackTop />
            <Header style={{ padding: 0, display: 'block', background: 'rgba(0, 0, 0, 0.85)' }}>
                <Row style={{ width: '100%', zIndex: 1 }}>
                    <Menu />
                </Row>
            </Header>
            <Content style={{ marginTop: '30px', marginBottom: '20px', marginLeft: '1px', display: 'flex' }}>
                <Container margin="auto" display={'block'}>
                    <>
                        {
                            pageNotFound && (
                                <Result
                                    style={{ margin: 'auto' }}
                                    status="404"
                                    title="Page not found"
                                />
                            )
                        }

                        {
                            (!pageError && pageLoaded) && (
                                <GalleryModalContent index={0} photos={photos} showGallery={true} id={photos[0].id} hidePreviewModal={true}/>
                            )
                        }

                        {
                            (pageError && !pageNotFound) && (
                                <Result
                                    style={{ margin: 'auto' }}
                                    status="error"
                                    title="Oops something went wrong..."
                                />
                            )
                        }
                    </>

                </Container>
            </Content>
            <CustomFooter />
        </Layout>
    )
}

export async function getServerSideProps({query}: any) {
    const res = await fetch(`${BACKEND_URL}${MEDIA_META_URL}${query?.id}`)
    const photos = await res.json()

    return {
      props: {photos: [photos]}, // will be passed to the page component as props
    }
  }
export default PreviewPage