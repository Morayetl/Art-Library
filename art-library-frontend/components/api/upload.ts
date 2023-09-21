import axiosInstance from "../axios"
import { IMedia } from "../types/IMedia";

const baseUrl = '/upload'
const uploadApi = {
    addMedia: (data: IMedia) => {
        const formData = new FormData();
        formData.append("file", data.file.file.originFileObj!);
        formData.append("description", data.description!);
        formData.append("title", data.title!);
        formData.append("tags", JSON.stringify(data.tags))

        return axiosInstance.post<string>(
            baseUrl,
            formData, 
            {
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                timeout: 200000
            }
        )
    },
    deleteMedia: (id: string) => {
        return axiosInstance.delete<string>(
            baseUrl + '/' + id
        )
    },
    updateMedia: (data: IMedia, id: string) => {
        return axiosInstance.put<string>(
            baseUrl + '/' + id,
            data
        )
    }
}

export default uploadApi;