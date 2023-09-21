import dynamic from "next/dynamic"
import { useEffect } from "react"

// we can either import Klaro without styles...
//@ts-ignore
//import * as Klaro from "klaro/dist/klaro-no-css";
// and the manually load the styles (e.g. to bundle them manually)
import "klaro/dist/klaro.css"

// or we can import Klaro with styles:
// import * as Klaro from "klaro"

const config = {
    translations: {
        en: {
            googleAnalytics: {
                title: "Google Analytics",
                description: "The analytics service ran by Google to improve services.",
            },
            googleAd: {
                title: "Google Ads",
                description: "The advertisement service ran by Google.",
            },
            purposes: {
                analytics: "Analytics",
                styling: "Styling",
                advertising: "Advertising"
            }
        }
    },
    apps: [
        {
            name: "googleAnalytics",
            purposes: ["analytics"],
            default: true
        },
        {
            name: "googleAd",
            purposes: ["advertising"],
            default: true,
            required: true
        },
        /*{
            name: "bootstrap",
            title: "Bootstrap (external resource)",
            description: "Example for embedding external stylesheets.",
            purposes: ["styling"],
        },*/
    ],
};


export const CookieConsent = () => {
    useEffect(() => {
        if (typeof window !== 'undefined' /*&& (window as any)?.klaro && (window as any)?.klaroConfig*/) {
            //@ts-ignore
            import("klaro").then((klaro)=>klaro.setup(config))
        }
    }, [window])

    return null;
}
const loader = () => Promise.resolve(CookieConsent);

export const CookieConsentDynamicComponentWithNoSSR = dynamic(
    loader,
    { ssr: false, }
)