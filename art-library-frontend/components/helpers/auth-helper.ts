import { COOKIE_NAME_AUTHORIZATION, COOKIE_NAME_USER_CREDENTIALS } from "../constants";
import { getCookie } from 'cookies-next';

/**
 * Used in serverside props
 * @param param0 
 */
export async function UserAccessRedirect({ req, res }: any) {
    const cookies = req.cookies;
    const allCookies = JSON.parse(JSON.stringify(cookies));
    const authCookie = allCookies[COOKIE_NAME_AUTHORIZATION]?.length > 0 ? allCookies[COOKIE_NAME_AUTHORIZATION] : undefined
    const credentials = allCookies[COOKIE_NAME_USER_CREDENTIALS]?.length > 0 ? JSON.parse(allCookies[COOKIE_NAME_USER_CREDENTIALS]) : undefined

    if (!authCookie || !credentials) {
        return {
            redirect: {
                destination: '/',
                statusCode: 307
            }
        }
    }
    
    return { props: {} }
}