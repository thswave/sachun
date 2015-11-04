@m = []
@m << ['0', '0', '0', '0', '0']
@m << ['0', '!', '@', '#', '0']
@m << ['0', '@', '#', '$', '0']
@m << ['0', '!', '0', '$', '0']
@m<< ['0', '0', '0', '0', '0']

def movable?(x, y)
    if x >= 5 || x < 0  ||  y >= 5 || y < 0 then
        return false
    end

    if marker?(x, y) then
        return false
    end 
    true
end

def marker?(x, y)
    if x >= 5 || x < 0  ||  y >= 5 || y < 0 then
        return false
    end
    if @a_x == x && @a_y == y then
        return false
    end
    @m[y][x] != '0'
end

def dest?(a_x, a_y)
    @m[a_y][a_x] == @m[@b_y][@b_x]
end

def valid_line_count?(line)
    line <= 3
end

def deleteMarker
    @m[@a_y][@a_x] = '0'
    @m[@b_y][@b_x] = '0'
end

def move(direction, line, a_x, a_y)
    unless valid_line_count? (line)
        return false;
    end

    if direction == 'e'  then
        a_x = a_x + 1  
        if movable?(a_x, a_y) then
            return move('e', line, a_x, a_y)  ||  move('w', line+1, a_x, a_y)  || move('s', line+1, a_x, a_y)  || move('n', line+1, a_x, a_y)    
        end

    elsif direction == 'w'  then
        a_x = a_x - 1    
        if movable?(a_x, a_y) then
            return move('e', line+1, a_x, a_y)  ||  move('w', line, a_x, a_y)  || move('s', line+1, a_x, a_y)  || move('n', line+1, a_x, a_y)
        end

    elsif direction == 's'  then  
        a_y = a_y + 1
        if movable?(a_x, a_y) then
            return move('e', line+1, a_x, a_y)  ||  move('w', line+1, a_x, a_y)  || move('s', line, a_x, a_y)  || move('n', line+1, a_x, a_y)
        end

    elsif direction == 'n'  then  
        a_y = a_y - 1
        if movable?(a_x, a_y) then
            return move('e', line+1, a_x, a_y)  ||  move('w', line+1, a_x, a_y)  || move('s', line+1, a_x, a_y)  || move('n', line, a_x, a_y)
        end
    end
    marker?(a_x, a_y) && dest?(a_x, a_y)
end


loop do
    p @m
    line = 0
    cmd = gets
    a = cmd.split(",")[0].to_i
    b = cmd.split(",")[1].to_i    
    @a_y = (a-1) / 5
    @a_x = (a-1) % 5
    @b_y = (b-1) / 5
    @b_x = (b-1) % 5
    if move('e', line+1, @a_x, @a_y)  ||  move('w', line+1, @a_x, @a_y)  || move('s', line+1, @a_x, @a_y)  || move('n', line+1, @a_x, @a_y) then
        p "true"
        deleteMarker
        p @m
    else
        p "false"
        p @m
    end
end