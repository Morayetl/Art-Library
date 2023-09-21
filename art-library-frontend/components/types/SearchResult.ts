import { PhotoPropsCustom } from "./PhotoPropsCustom";

export default interface SearchResult{
    page: number,
    count: number,
    results: Array<PhotoPropsCustom>
    pageSize: number
}