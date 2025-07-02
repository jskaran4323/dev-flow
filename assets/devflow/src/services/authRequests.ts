import request from './request'

export const registerUser = (data: {
  username: string
  email: string
  password: string
  fullname: string

}) => request.post('/auth/register', data)

export const loginUser = (data: {
  identifier: string
  password: string
}) => request.post('/auth/login', data)
