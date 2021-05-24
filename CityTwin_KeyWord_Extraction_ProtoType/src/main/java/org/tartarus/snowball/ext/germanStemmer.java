// Generated by Snowball 2.1.0 - https://snowballstem.org/

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;

/**
 * This class implements the stemming algorithm defined by a snowball script.
 * <p>
 * Generated by Snowball 2.1.0 - https://snowballstem.org/
 * </p>
 */
@SuppressWarnings("unused")
public class germanStemmer extends org.tartarus.snowball.SnowballStemmer {

    private static final long serialVersionUID = 1L;

private final static Among a_0[] = {
    new Among("", -1, 5),
    new Among("U", 0, 2),
    new Among("Y", 0, 1),
    new Among("\u00E4", 0, 3),
    new Among("\u00F6", 0, 4),
    new Among("\u00FC", 0, 2)
};

private final static Among a_1[] = {
    new Among("e", -1, 2),
    new Among("em", -1, 1),
    new Among("en", -1, 2),
    new Among("ern", -1, 1),
    new Among("er", -1, 1),
    new Among("s", -1, 3),
    new Among("es", 5, 2)
};

private final static Among a_2[] = {
    new Among("en", -1, 1),
    new Among("er", -1, 1),
    new Among("st", -1, 2),
    new Among("est", 2, 1)
};

private final static Among a_3[] = {
    new Among("ig", -1, 1),
    new Among("lich", -1, 1)
};

private final static Among a_4[] = {
    new Among("end", -1, 1),
    new Among("ig", -1, 2),
    new Among("ung", -1, 1),
    new Among("lich", -1, 3),
    new Among("isch", -1, 2),
    new Among("ik", -1, 2),
    new Among("heit", -1, 3),
    new Among("keit", -1, 4)
};

private static final char g_v[] = {17, 65, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 32, 8 };

private static final char g_s_ending[] = {117, 30, 5 };

private static final char g_st_ending[] = {117, 30, 4 };

private int I_x;
private int I_p2;
private int I_p1;


private boolean r_prelude() {
    int v_1 = cursor;
    while(true)
    {
        int v_2 = cursor;
        lab0: {
            lab1: {
                int v_3 = cursor;
                lab2: {
                    bra = cursor;
                    if (!(eq_s("\u00DF")))
                    {
                        break lab2;
                    }
                    ket = cursor;
                    slice_from("ss");
                    break lab1;
                }
                cursor = v_3;
                if (cursor >= limit)
                {
                    break lab0;
                }
                cursor++;
            }
            continue;
        }
        cursor = v_2;
        break;
    }
    cursor = v_1;
    while(true)
    {
        int v_4 = cursor;
        lab3: {
            golab4: while(true)
            {
                int v_5 = cursor;
                lab5: {
                    if (!(in_grouping(g_v, 97, 252)))
                    {
                        break lab5;
                    }
                    bra = cursor;
                    lab6: {
                        int v_6 = cursor;
                        lab7: {
                            if (!(eq_s("u")))
                            {
                                break lab7;
                            }
                            ket = cursor;
                            if (!(in_grouping(g_v, 97, 252)))
                            {
                                break lab7;
                            }
                            slice_from("U");
                            break lab6;
                        }
                        cursor = v_6;
                        if (!(eq_s("y")))
                        {
                            break lab5;
                        }
                        ket = cursor;
                        if (!(in_grouping(g_v, 97, 252)))
                        {
                            break lab5;
                        }
                        slice_from("Y");
                    }
                    cursor = v_5;
                    break golab4;
                }
                cursor = v_5;
                if (cursor >= limit)
                {
                    break lab3;
                }
                cursor++;
            }
            continue;
        }
        cursor = v_4;
        break;
    }
    return true;
}

private boolean r_mark_regions() {
    I_p1 = limit;
    I_p2 = limit;
    int v_1 = cursor;
    {
        int c = cursor + 3;
        if (c > limit)
        {
            return false;
        }
        cursor = c;
    }
    I_x = cursor;
    cursor = v_1;
    golab0: while(true)
    {
        lab1: {
            if (!(in_grouping(g_v, 97, 252)))
            {
                break lab1;
            }
            break golab0;
        }
        if (cursor >= limit)
        {
            return false;
        }
        cursor++;
    }
    golab2: while(true)
    {
        lab3: {
            if (!(out_grouping(g_v, 97, 252)))
            {
                break lab3;
            }
            break golab2;
        }
        if (cursor >= limit)
        {
            return false;
        }
        cursor++;
    }
    I_p1 = cursor;
    lab4: {
        if (!(I_p1 < I_x))
        {
            break lab4;
        }
        I_p1 = I_x;
    }
    golab5: while(true)
    {
        lab6: {
            if (!(in_grouping(g_v, 97, 252)))
            {
                break lab6;
            }
            break golab5;
        }
        if (cursor >= limit)
        {
            return false;
        }
        cursor++;
    }
    golab7: while(true)
    {
        lab8: {
            if (!(out_grouping(g_v, 97, 252)))
            {
                break lab8;
            }
            break golab7;
        }
        if (cursor >= limit)
        {
            return false;
        }
        cursor++;
    }
    I_p2 = cursor;
    return true;
}

private boolean r_postlude() {
    int among_var;
    while(true)
    {
        int v_1 = cursor;
        lab0: {
            bra = cursor;
            among_var = find_among(a_0);
            if (among_var == 0)
            {
                break lab0;
            }
            ket = cursor;
            switch (among_var) {
                case 1:
                    slice_from("y");
                    break;
                case 2:
                    slice_from("u");
                    break;
                case 3:
                    slice_from("a");
                    break;
                case 4:
                    slice_from("o");
                    break;
                case 5:
                    if (cursor >= limit)
                    {
                        break lab0;
                    }
                    cursor++;
                    break;
            }
            continue;
        }
        cursor = v_1;
        break;
    }
    return true;
}

private boolean r_R1() {
    if (!(I_p1 <= cursor))
    {
        return false;
    }
    return true;
}

private boolean r_R2() {
    if (!(I_p2 <= cursor))
    {
        return false;
    }
    return true;
}

private boolean r_standard_suffix() {
    int among_var;
    int v_1 = limit - cursor;
    lab0: {
        ket = cursor;
        among_var = find_among_b(a_1);
        if (among_var == 0)
        {
            break lab0;
        }
        bra = cursor;
        if (!r_R1())
        {
            break lab0;
        }
        switch (among_var) {
            case 1:
                slice_del();
                break;
            case 2:
                slice_del();
                int v_2 = limit - cursor;
                lab1: {
                    ket = cursor;
                    if (!(eq_s_b("s")))
                    {
                        cursor = limit - v_2;
                        break lab1;
                    }
                    bra = cursor;
                    if (!(eq_s_b("nis")))
                    {
                        cursor = limit - v_2;
                        break lab1;
                    }
                    slice_del();
                }
                break;
            case 3:
                if (!(in_grouping_b(g_s_ending, 98, 116)))
                {
                    break lab0;
                }
                slice_del();
                break;
        }
    }
    cursor = limit - v_1;
    int v_3 = limit - cursor;
    lab2: {
        ket = cursor;
        among_var = find_among_b(a_2);
        if (among_var == 0)
        {
            break lab2;
        }
        bra = cursor;
        if (!r_R1())
        {
            break lab2;
        }
        switch (among_var) {
            case 1:
                slice_del();
                break;
            case 2:
                if (!(in_grouping_b(g_st_ending, 98, 116)))
                {
                    break lab2;
                }
                {
                    int c = cursor - 3;
                    if (c < limit_backward)
                    {
                        break lab2;
                    }
                    cursor = c;
                }
                slice_del();
                break;
        }
    }
    cursor = limit - v_3;
    int v_4 = limit - cursor;
    lab3: {
        ket = cursor;
        among_var = find_among_b(a_4);
        if (among_var == 0)
        {
            break lab3;
        }
        bra = cursor;
        if (!r_R2())
        {
            break lab3;
        }
        switch (among_var) {
            case 1:
                slice_del();
                int v_5 = limit - cursor;
                lab4: {
                    ket = cursor;
                    if (!(eq_s_b("ig")))
                    {
                        cursor = limit - v_5;
                        break lab4;
                    }
                    bra = cursor;
                    {
                        int v_6 = limit - cursor;
                        lab5: {
                            if (!(eq_s_b("e")))
                            {
                                break lab5;
                            }
                            cursor = limit - v_5;
                            break lab4;
                        }
                        cursor = limit - v_6;
                    }
                    if (!r_R2())
                    {
                        cursor = limit - v_5;
                        break lab4;
                    }
                    slice_del();
                }
                break;
            case 2:
                {
                    int v_7 = limit - cursor;
                    lab6: {
                        if (!(eq_s_b("e")))
                        {
                            break lab6;
                        }
                        break lab3;
                    }
                    cursor = limit - v_7;
                }
                slice_del();
                break;
            case 3:
                slice_del();
                int v_8 = limit - cursor;
                lab7: {
                    ket = cursor;
                    lab8: {
                        int v_9 = limit - cursor;
                        lab9: {
                            if (!(eq_s_b("er")))
                            {
                                break lab9;
                            }
                            break lab8;
                        }
                        cursor = limit - v_9;
                        if (!(eq_s_b("en")))
                        {
                            cursor = limit - v_8;
                            break lab7;
                        }
                    }
                    bra = cursor;
                    if (!r_R1())
                    {
                        cursor = limit - v_8;
                        break lab7;
                    }
                    slice_del();
                }
                break;
            case 4:
                slice_del();
                int v_10 = limit - cursor;
                lab10: {
                    ket = cursor;
                    if (find_among_b(a_3) == 0)
                    {
                        cursor = limit - v_10;
                        break lab10;
                    }
                    bra = cursor;
                    if (!r_R2())
                    {
                        cursor = limit - v_10;
                        break lab10;
                    }
                    slice_del();
                }
                break;
        }
    }
    cursor = limit - v_4;
    return true;
}

public boolean stem() {
    int v_1 = cursor;
    r_prelude();
    cursor = v_1;
    int v_2 = cursor;
    r_mark_regions();
    cursor = v_2;
    limit_backward = cursor;
    cursor = limit;
    r_standard_suffix();
    cursor = limit_backward;
    int v_4 = cursor;
    r_postlude();
    cursor = v_4;
    return true;
}

@Override
public boolean equals( Object o ) {
    return o instanceof germanStemmer;
}

@Override
public int hashCode() {
    return germanStemmer.class.getName().hashCode();
}



}

