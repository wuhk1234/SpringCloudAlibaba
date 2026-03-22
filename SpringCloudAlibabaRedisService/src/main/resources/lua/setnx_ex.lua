local lockKey = KEYS[1]
local lockValue = KEYS[2]
local expire = KEYS[3]

local result_nx = redis.call('SETNX',lockKey,lockValue)
if result_nx == 1 then
    local result_ex = redis.call('EXPIRE',lockKey,expire)
    return result_ex
else
    return result_nx
end

