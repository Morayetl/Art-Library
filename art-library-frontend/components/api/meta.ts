import axiosInstance from "../axios"
import { PhotoPropsCustom } from "../types/PhotoPropsCustom";

const baseUrl = '/meta'
const metaApi = {
    get: (id:string) => {
        return axiosInstance.get<PhotoPropsCustom>(
            baseUrl +'/' + id,
        )
    }
}

export default metaApi;