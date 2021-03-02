
function generateFetchConfig(method, body = null) {
    const upCaseMethod = method.toUpperCase();
    const config = {
        'Content-Type' : 'application/json'
    }
    if(['POST', 'PUT'].includes(upCaseMethod)){
        config.body = JSON.stringify(body);
    }
    return config;
}

export function fetchPosts(endpoint){
    return fetch(endpoint);
}

export function fetchPost(id) {

}