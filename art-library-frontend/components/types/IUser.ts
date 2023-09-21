
export default interface IUser{
    id: string,
    username: string,
    email: string,
    roles: Array<string>,
    jwt: string
}