import { NextPage } from 'next'
import { BackTop, Layout, Row, Typography } from 'antd';
import Search from '../components/home/Search';
import Container from '../components/shared/Container';
import Gallery from '../components/shared/Gallery';

import '../styles/Home.module.css'
import Menu from '../components/shared/Menu';
import Ad from '../components/shared/Ad';
import { useEffect, useState } from 'react';
import { PhotoPropsCustom } from '../components/types/PhotoPropsCustom';
import searchApi from '../components/api/search';
import CustomFooter from '../components/shared/CustomFooter';
import { testDataImages } from '../test-data/images';
import Head from 'next/head';
import { FRONTEND_URL, SITE_DESCRIPTION, SITE_NAME } from '../components/constants';
import { AxiosError } from 'axios';

const { Header, Content } = Layout;
const { Title, Text } = Typography;


const imageHeight = "500px";
const headerPic = '/home_photo.jpg';
const Home: NextPage = () => {
  const [serverError, setServerError] = useState<boolean>(false);
  const [photos, setPhotos] = useState<Array<PhotoPropsCustom>>([]);
  const [page, setPage] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(0);
  const [amountOfResults, setamountOfResults] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [allResult, setAllResult] = useState<Array<PhotoPropsCustom>>([]);

  useEffect(() => {
    // search for all results
    loadImages();
  }, [])

  const loadImages = (newPage?: number) => {
    setLoading(true)

    // sets the next page of random results
    if(newPage && newPage > 0 && pageSize) {
      let newResult:Array<PhotoPropsCustom> = [] 
      const start = pageSize * newPage;
      const end = start + pageSize;
      allResult.map((res, index)=> {
        if(index > start && index <= end){
          newResult.push(res);
        }
      })

      setPhotos([
        ...photos,
        ...newResult
      ]);

      setPage(newPage);
      return;
    }

    searchApi.search("", newPage || page, true)
      .then((res) => {
        const result = res.data
        const allRes = result.results.filter(r => r?.type?.includes('image'));
        let firstPage:Array<PhotoPropsCustom> = []
        allRes.map((res, index)=> {
          if(index >= 0 && index <= result.pageSize){
            firstPage.push(res);
          }
        })

        setPage(result.page);
        // sets the first 30 results
        setPhotos(firstPage);

        // saves the rest of the results and loads them when user scrolls
        setAllResult(allRes)

        // amount of results all together
        setamountOfResults(result.count);

        // maximum page size
        setPageSize(result.pageSize);
      })
      .catch((e:AxiosError) => {
        if(!e.response?.status || e.response?.status <= 500){
          setServerError(true);
        }
      }).finally(() => {
        setLoading(false)
      })
  }

  return (
    <Layout>
      <Head>
        <title>{`${SITE_NAME}`}</title>
        <meta name="title" content={`${SITE_NAME}`} />
        <meta name="description" content={SITE_DESCRIPTION} />

        <meta property="og:type" content="website" />
        <meta property="og:url" content={FRONTEND_URL} />
        <meta property="og:title" content={`${SITE_NAME}`} />
        <meta property="og:description" content={SITE_DESCRIPTION} />
        <meta property="og:image" content={FRONTEND_URL + headerPic} />

        <meta property="twitter:card" content="summary_large_image" />
        <meta property="twitter:url" content={FRONTEND_URL} />
        <meta property="twitter:title" content={`${SITE_NAME}`} />
        <meta property="twitter:description" content={SITE_DESCRIPTION} />
        <meta property="twitter:image" content={FRONTEND_URL + headerPic} />
      </Head>
      <BackTop />
      <Header style={{ padding: 0, height: imageHeight, background: 'transparent' }}>
        <img style={{ height: imageHeight, width: '100%', position: 'absolute', objectFit: 'cover' }} src={headerPic} />
        <div className="home-hero-image" style={{ height: imageHeight, width: '100%' }} />
        <Row style={{ width: '100%', zIndex: 1 }}>
          <Menu />
        </Row>

        <Row style={{ width: '100%' }}>
          <Container margin="auto">
            <Row style={{ zIndex: 1, marginLeft: '10px', marginRight: '10px', marginTop: '70px', marginBottom: "130px" }}>
              <Row style={{ margin: 'auto' }}>
                <Title style={{ overflowWrap: 'anywhere', textAlign: 'center', color: 'white' }}>
                  Royalty free Pictures and Images for any use.
                </Title>
              </Row>
              <Row style={{ margin: 'auto', width: '100%' }} >
                <Search marginBottom="10px" redirectToSearchPage />
              </Row>
              <Row style={{ margin: 'auto' }}>
                <Text style={{ overflowWrap: 'anywhere', textAlign: 'center', color: 'white' }}>
                  Over 1000 free Pictures and Videos
                </Text>
              </Row>
            </Row>
          </Container>
        </Row>
      </Header>
      <Content style={{ marginTop: '2px', marginBottom: '2px', marginLeft: '1px' }}>
        {/* <Ad id="front-page-ad" className="gallery-ad" /> */}
        <Gallery loading={loading} photos={photos} onPageLoad={loadImages} resultCount={amountOfResults} currentPage={page} pageSize={pageSize} serverError={serverError} />
      </Content>
      <CustomFooter />
    </Layout>
  )
}

export default Home
