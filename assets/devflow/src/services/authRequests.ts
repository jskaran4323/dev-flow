import request from './request'

export const registerUser = (data: {
  username: string
  email: string
  password: string
  fullName: string

}) => request.post('/auth/register', data)

export const loginUser = (data: {
  identifier: string
  password: string
}) => request.post('/auth/login', data)


export const addProject = (data:{
    name: string;
    description: string;
    status: number
}) => request.post('api/projects/register', data)