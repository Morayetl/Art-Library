import '../styles/globals.css'
import 'antd/dist/antd.css';
import '../styles/custom-antd.css'
import { AppProps } from 'next/app'
import { ModalProvider } from '../components/shared/Modal/ModalContext';
import Head from 'next/head';
import { SITE_DESCRIPTION, SITE_NAME } from '../components/constants';
import { CookieConsentDynamicComponentWithNoSSR } from '../components/shared/CookieConsent';

function MyApp({ Component, pageProps }: AppProps) {

  return (
    <ModalProvider>
      <>
        <Head>
          <title>{`${SITE_NAME}`}</title>
          <meta name="title" content={`${SITE_NAME}`} />
          <meta name="description" content={SITE_DESCRIPTION} />
        </Head>
        <CookieConsentDynamicComponentWithNoSSR />
        <div id="modal-root"></div>
        <Component {...pageProps} />
      </>
    </ModalProvider>
  )


}

export default MyApp
