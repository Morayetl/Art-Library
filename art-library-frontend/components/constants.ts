export const ADSENSE_CONFIG = {
    client: 'ca-pub-3857165649571444',
    slot: '8139478739',
    src: 'https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js'
}

export const isDevelopment = process.env.NODE_ENV === "development" ? true : false;
export const SITE_NAME = "YourSiteName";
export const BACKEND_URL = isDevelopment ? 'http://localhost:8080/api' : `https://www.${SITE_NAME}.com/api`;
export const FRONTEND_URL = isDevelopment ? 'http://localhost:3000' : `https://www.${SITE_NAME.toLowerCase()}.com`;

export const SITE_DESCRIPTION = 'Royalty free Pictures and Images for any use.';

export const COOKIE_NAME_USER_CREDENTIALS = SITE_NAME.toLowerCase() + '_' + "crendentials"
export const COOKIE_NAME_AUTHORIZATION = 'authorization';
export const NEXT_PUBLIC_GOOGLE_ANALYTICS = '';
export const NEXT_PUBLIC_GOOGLE_AD = '';