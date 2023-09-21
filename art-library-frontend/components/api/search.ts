import axiosInstance from "../axios"
import SearchResult from "../types/SearchResult";

const baseUrl = '/search'
const searchApi = {
    search: (query: string, page:number, random?: boolean) => {
        return axiosInstance.get<SearchResult>(
            baseUrl, 
            { 
                params: {
                    query,
                    page,
                    random
                } 
            }
        )
    },
    searchUser: (query: string, page:number) => {
        return axiosInstance.get<SearchResult>(
            baseUrl + '/user', 
            { 
                params: {
                    query,
                    page
                } 
            }
        )
    }
}

export default searchApi;