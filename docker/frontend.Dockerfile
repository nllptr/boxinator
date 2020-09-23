FROM node:14.11.0-alpine3.10

RUN apk add yarn

WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

COPY frontend/package.json .
COPY frontend/yarn.lock .
RUN yarn install
RUN yarn global add react-scripts@3.4.1

# add app
COPY ./frontend .

# start app
CMD ["yarn", "start"]