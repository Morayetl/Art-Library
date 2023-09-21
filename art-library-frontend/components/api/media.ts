import axiosInstance from "../axios"
import { PhotoPropsCustom } from "../types/PhotoPropsCustom"

const baseUrl = '/media'
export const MEDIA_META_URL = baseUrl +'/meta/'

const mediaApi = {
    download: (id:string | undefined, gcaptcha: string) => {
        return axiosInstance.get<File>(
            baseUrl +'/download/' + id,
            {
                responseType: 'blob',
                params: {
                    "g-recaptcha-response": gcaptcha
                },
                timeout: 200000
            }
        )
    },
    getMeta: (id:string) => {
        return axiosInstance.get<PhotoPropsCustom>(
            MEDIA_META_URL + id
        )
    }
}

export default mediaApi;