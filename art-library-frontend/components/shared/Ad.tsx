import { useEffect } from "react"
import { ADSENSE_CONFIG } from "../constants";


export type AdProps = {
    width?: number,
    height?:number,
    id?: string,
    className?: string
}
export default function Ad(props: AdProps) {
    /*useEffect(() => {
        if (typeof window !== 'undefined') {
            var ads = document.getElementsByClassName("adsbygoogle").length;
            for (var i = 0; i < ads; i++) {
                try {
                    //@ts-ignore
                    (window.adsbygoogle = window.adsbygoogle || []).push({});
                } catch (e) { }
            }
        }
    }, []);*/

    return (
        <ins
            className={`adsbygoogle ${props.className}`}
            id={props.id}
            style={{ display: "inline-block"}}
            data-ad-client={ADSENSE_CONFIG.client}
            data-ad-slot={ADSENSE_CONFIG.slot}
            data-adtest="on"
        />
    )
}