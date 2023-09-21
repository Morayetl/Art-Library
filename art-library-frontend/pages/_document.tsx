import { Html, Head, Main, NextScript } from 'next/document';
import { ADSENSE_CONFIG, NEXT_PUBLIC_GOOGLE_ANALYTICS } from '../components/constants';


export default function Document() {
    return (
        <Html>
            <Head>
                <link rel="icon" type="image/x-icon" href="/icon.png" />
                <script src='https://www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit' async defer />
                {/* <script
                    async
                    src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"
                /> 
                <script
                    data-type="text/javascript"
                    data-name="googleAd"
                    dangerouslySetInnerHTML={{
                        __html: `
                (adsbygoogle = window.adsbygoogle || []).push({
                    google_ad_client: "${ADSENSE_CONFIG.client}",
                    enable_page_level_ads: true
                });
                `
                    }}
                /> */}
                <script data-type="text/javascript" data-name="googleAd" async data-src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3857165649571444" crossOrigin="anonymous"></script>
                <script data-type="text/javascript" data-name="googleAnalytics" async data-src={`https://www.googletagmanager.com/gtag/js?id=${NEXT_PUBLIC_GOOGLE_ANALYTICS}`} />
                <script
                    data-type="text/javascript"
                    data-name="googleAnalytics"
                    dangerouslySetInnerHTML={{
                        __html: `
                        window.dataLayer = window.dataLayer || [];
                        function gtag(){dataLayer.push(arguments);}
                        gtag('js', new Date());
                        gtag('config', '${NEXT_PUBLIC_GOOGLE_ANALYTICS}', {
                        page_path: window.location.pathname,
                        });
                `
                    }}
                />
            </Head>
            <body>
                <Main />
                <NextScript />
            </body>
        </Html>
    )
}