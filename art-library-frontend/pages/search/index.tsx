import { BackTop, Layout, Row, Spin, Typography } from 'antd'
import { AxiosError } from 'axios';
import { useRouter } from 'next/router'
import { useEffect, useState } from 'react';
import searchApi from '../../components/api/search';
import Search from '../../components/home/Search';
import Ad from '../../components/shared/Ad';
import Container from '../../components/shared/Container'
import CustomFooter from '../../components/shared/CustomFooter';
import Gallery from '../../components/shared/Gallery';
import Menu from '../../components/shared/Menu';
import { PhotoPropsCustom } from '../../components/types/PhotoPropsCustom';

const { Header, Content } = Layout;
const { Title, Text } = Typography;

const imageHeight = "500px";
type SearchPageProps = {
  withoutQuery?: boolean
}

const SearchPage = (props: SearchPageProps) => {
  const router = useRouter()
  const { q } = router.query

  const [photos, setPhotos] = useState<Array<PhotoPropsCustom>>([]);
  const [page, setPage] = useState<number>(0);
  const [amountOfResults, setamountOfResults] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [resultNotFound, setResultNotFound] = useState<boolean>(false);
  const [serverError, setServerError] = useState<boolean>(false);
  const [query, setQuery] = useState<string>("");
  const [pageSize, setPageSize] = useState<number>(0);

  useEffect(() => {
    // if there are already photos, initialization is already made
    if (photos.length > 0 || !router.isReady) {
      return;
    }

    // search for all results with params
    if (q) {
      loadImages(0, q as string, true);
      setQuery(q as string);
    }

    // search for all results without params
    if (!q) {
      loadImages(0, '', true, true);
    }

  }, [q, router.isReady])

  const loadImages = (newPage?: number, searchQuery?: string, newSearch?: boolean, random = false) => {
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
    const search = newSearch ? searchQuery : query

    setLoading(true);
    searchApi.search(search || '', nextPage || 0, random)
      .then((res) => {
        setServerError(false);
        const result = res.data
        setPage(result.page);
        setPhotos([
          ...(newSearch ? [] : photos),
          ...result.results.filter(r => r?.type?.includes('image'))
        ]);
        setamountOfResults(result.count);
        setPageSize(result.pageSize)
        setResultNotFound(result.results.length === 0);

      })
      .catch((e: AxiosError) => {
        if (!e.response?.status || e.response?.status >= 500) {
          setServerError(true);
        }
      }).finally(() => {
        setTimeout(() => {
          setLoading(false);
        }, 300)
      });
  }

  return (
    <Layout>
      <BackTop />
      <Header style={{ padding: 0, height: imageHeight, background: 'transparent' }}>
        <img style={{ height: imageHeight, width: '100%', position: 'absolute', objectFit: 'cover' }} src="/search_page_photo.jpg" />
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
                <Search defaultValue={q as string} marginBottom="10px" onSearch={(query, page) => loadImages(page, query, true)} loading={loading} />
              </Row>
              <Row style={{ margin: 'auto' }}>
                <Text style={{ overflowWrap: 'anywhere', textAlign: 'center', color: 'white' }}>
                  {query ? `Results for "${query}"` : null}
                </Text>
              </Row>
            </Row>
          </Container>
        </Row>
      </Header>
      <Content style={{ marginTop: '2px', marginBottom: '2px', marginLeft: '1px' }}>
        <Ad id="front-page-ad" className="gallery-ad" />
        <Gallery
          photos={photos}
          onPageLoad={loadImages}
          resultCount={amountOfResults}
          currentPage={page}
          loading={loading}
          resultNotFound={resultNotFound}
          pageSize={pageSize}
          serverError={serverError}
        />
      </Content>
      <CustomFooter />
    </Layout>
  )
}

export default SearchPage